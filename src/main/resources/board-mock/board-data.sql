
/*게시물 1000000 건 생성*/
INSERT INTO board (created_date, last_modified_date, member_id,  created_by, last_modified_by, title, content)
WITH RECURSIVE numbers AS (
    SELECT /*+ SET_VAR(cte_max_recursion_depth = 1000000) */
        1 as n
    UNION ALL
    SELECT n + 1 FROM numbers WHERE n < 1000000
)
SELECT
    now(),
    now(),
    1,
    'ei111',
    'ei111',
    CONCAT('Title ', n),
    CONCAT('content ', n)
FROM numbers;