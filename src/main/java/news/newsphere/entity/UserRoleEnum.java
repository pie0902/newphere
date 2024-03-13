package news.newsphere.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자");
    private final String key;
    private final String title;
}

//좋아요,북마크,댓글,팔로잉
//팔로잉 테이블 (user_id)

//같은 repository를 impl 형태로 spring data jpa,queryDSL(join문사용,sub query)
//JPQL(검색해보자)
//(리프레시토큰 레디스)
