-- 게시글 테이블
CREATE TABLE post
(
    post_id              BIGSERIAL PRIMARY KEY,              -- 고유 식별자
    board_name_eng      VARCHAR(255) NOT NULL,              -- 게시판명_eng
    board_name_kor      VARCHAR(255) NOT NULL,              -- 게시판명_kor
    post_no         varchar(255) NOT NULL,              -- 번호
    post_title      VARCHAR(255) NOT NULL,              -- 제목
    post_write_date TIMESTAMP,                          -- 게시일
    post_department TEXT         NOT NULL,              -- 부서
    post_url        TEXT         NOT NULL,              -- url
    monitor_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 모니터시점
);



-- 이벤트 테이블
CREATE TABLE event
(
    event_id   BIGSERIAL PRIMARY KEY, -- 고유 식별자
    event_data TEXT NOT NULL,         -- 이벤트 내용
    finished  BOOLEAN DEFAULT FALSE  -- 게시 여부 (기본값: FALSE)
);

-- 이벤트 타입 테이블
CREATE TABLE event_type
(
    event_type_id         BIGSERIAL PRIMARY KEY, -- 고유 식별자
    event_type VARCHAR(255) UNIQUE NOT NULL  -- 이벤트 타입
);

-- 이벤트와 이벤트 타입 매핑 테이블
CREATE TABLE map_event_event_type
(
    event_id      BIGINT NOT NULL, -- 이벤트 ID
    event_type_id BIGINT NOT NULL, -- 이벤트 타입 ID
    published  BOOLEAN DEFAULT false,  -- 게시 여부 (기본값: FALSE)
    PRIMARY KEY (event_id, event_type_id)
);

-- 외래 키 제약 조건 추가
ALTER TABLE map_event_event_type
    ADD CONSTRAINT fk_map_event_event_type_event_id
        FOREIGN KEY (event_id)
            REFERENCES event (event_id);

ALTER TABLE map_event_event_type
    ADD CONSTRAINT fk_map_event_event_type_event_type_id
        FOREIGN KEY (event_type_id)
            REFERENCES event_type (event_type_id);


INSERT INTO event_type(event_type) VALUES('NEW_POST');