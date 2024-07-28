-- 게시글 테이블
CREATE TABLE post
(
    id              BIGSERIAL PRIMARY KEY,              -- 고유 식별자
    board_name      VARCHAR(255) NOT NULL,              -- 게시판명
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
    id         BIGSERIAL PRIMARY KEY, -- 고유 식별자
    event_data JSON NOT NULL          -- 이벤트 내용
);

-- 이벤트 타입 테이블
CREATE TABLE event_type
(
    id         BIGSERIAL PRIMARY KEY, -- 고유 식별자
    event_type VARCHAR(255) NOT NULL  -- 이벤트 타입
);

-- 이벤트와 이벤트 타입 매핑 테이블
CREATE TABLE map_event_event_type
(
    event_id      BIGINT NOT NULL, -- 이벤트 ID
    event_type_id BIGINT NOT NULL, -- 이벤트 타입 ID
    PRIMARY KEY (event_id, event_type_id)
);

-- 외래 키 제약 조건 추가
ALTER TABLE map_event_event_event_type
    ADD CONSTRAINT fk_map_event_event_type_event_id
        FOREIGN KEY (event_id)
            REFERENCES event (id);

ALTER TABLE map_event_event_event_type
    ADD CONSTRAINT fk_map_event_event_type_event_type_id
        FOREIGN KEY (event_type_id)
            REFERENCES event_type (id);