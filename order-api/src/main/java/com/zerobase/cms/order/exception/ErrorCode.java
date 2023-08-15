package com.zerobase.cms.order.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST,"아이템 명 중복입니다."),
    NO_EXIST_PRODUCT(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private  final String detail;
}
