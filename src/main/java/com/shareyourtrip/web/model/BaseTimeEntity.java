package com.shareyourtrip.web.model;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
// Entity 클래스 상속시 BaseTimeEntity의 필드들도 컬럼으로 인식
@MappedSuperclass
// Auditing 기능 추가
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // Entity가 생성되어 저장된 시간
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity의 값이 변경될 때 시간
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
