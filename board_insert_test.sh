#!/bin/bash

#실행 명령어
# chmod +x send_coupon_issue_requests.sh
# ./send_coupon_issue_requests.sh
# API 엔드포인트를 변수로 설정
API_ENDPOINT="http://localhost:8080/board/v1/form"
JWT_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJBRE1JTiIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzUxMDc0NzU4LCJleHAiOjE3NTExNjExNTh9.2R83OEWLd5thPQ9WfWn3xnR6s-P0YRAWNlzwOFxLkjA"

# userId 1부터 500까지 총 2번씩 반복하여 요청 보내기
for userId in {1..500}; do
    for i in {1..2}; do
        # 요청 바디에 해당하는 JSON 데이터 생성
          REQUEST_BODY="{
                 \"content\": \"내용$userId\",
                 \"created_by\": \"admin\",
                 \"created_date\": \"2025-05-13\",
                 \"last_modified_date\": \"2024-11-26\",
                 \"member_id\": 1,
                 \"title\": \"content$userId\"
             }"

        # curl 명령어로 POST 요청을 보내고 응답 받기
        curl -X POST \
             -H "Content-Type: application/json" \
             -b "access_token=$JWT_TOKEN" \
             -d "$REQUEST_BODY" \
             "$API_ENDPOINT"
    done
done
