package com.zerobase.cms.order.application;

import com.zerobase.cms.order.client.MailgunClient;
import com.zerobase.cms.order.client.UserClient;
import com.zerobase.cms.order.client.mailgun.SendMailForm;
import com.zerobase.cms.order.client.user.ChangeBalanceForm;
import com.zerobase.cms.order.client.user.CustomerDto;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.redis.Cart;
import com.zerobase.cms.order.exception.CustomException;
import com.zerobase.cms.order.exception.ErrorCode;
import com.zerobase.cms.order.service.ProductItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.zerobase.cms.order.exception.ErrorCode.ORDER_FAIL_NOT_ENOUGH_MONEY;

@Service
@RequiredArgsConstructor
public class OrderApplication {
    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemServiceImpl productItemServiceImpl;
    private final MailgunClient mailgunClient;

    @Transactional
    public void order(String token, Cart cart) throws CustomException {
        Cart orderCart = cartApplication.refreshCart(cart);
        if (orderCart.getMessages().size() > 0) {
            throw new CustomException(ErrorCode.ORDER_FAIL_CHECK_CART);
        }
        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();

        int totalPrice = getTotalPrice(cart);
        if (customerDto.getBalance() < totalPrice) {
            throw new CustomException(ORDER_FAIL_NOT_ENOUGH_MONEY);
        }

        userClient.changeBalance(token, ChangeBalanceForm.builder()
                .from("USER")
                .message("Order")
                .money(-totalPrice)
                .build());

        SendMailForm sendMailForm = SendMailForm.builder()
                .from("zerobase-test@mytest.com")
                .to(customerDto.getEmail())
                .subject("Order List")
                .text(emailBody(cart))
                .build();
        mailgunClient.sendEmail(sendMailForm);

        for (Cart.Product product : orderCart.getProducts()) {
            for (Cart.ProductItem cartItem : product.getItems()) {
                ProductItem productItem =
                        productItemServiceImpl.getProductItem(cartItem.getId());
                productItem.setCount(
                        productItem.getCount() - cartItem.getCount());

            }
        }
    }

    private Integer getTotalPrice(Cart cart) {
        return cart.getProducts().stream()
                .flatMapToInt(product ->
                        product.getItems().stream()
                                .flatMapToInt(productItem ->
                                        IntStream.of(
                                                productItem.getPrice() *
                                                        productItem.getCount())))
                .sum();
    }

    private String emailBody(Cart cart) {
        StringBuilder sb = new StringBuilder();
        for (Cart.Product product : cart.getProducts()) {
            sb.append("상품명: ")
                    .append(product.getName())
                    .append(" 상품 설명: ")
                    .append(product.getDescription()+"\n");
            for (Cart.ProductItem productItem : product.getItems()) {
                sb.append("사이즈: ")
                        .append(productItem.getName())
                        .append(" 수량: ")
                        .append(productItem.getCount())
                        .append(" 가격: ")
                        .append(productItem.getPrice());
            }
        }
        return sb.toString();
    }
}
