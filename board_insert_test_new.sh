#!/bin/bash

# API 엔드포인트
API_ENDPOINT="http://localhost:8080/coupon/v1/coupons/issue"

# JWT 토큰 필요 시 활성화
# JWT_TOKEN="..."

# userId 1부터 500까지, 각각 2번 반복
for userId in $(seq 1 500); do
    for i in $(seq 1 2); do

        # -----------------------------
        # JSON 요청 바디 생성 (here document)
        # -----------------------------
        REQUEST_BODY=$(cat <<EOF
{
  "couponId": "1",
  "userId": "$userId",
  "couponName": "쇼핑쿠폰",
  "couponCode": "A1"
}
EOF
)

        # -----------------------------
        # curl 요청 보내기
        # -----------------------------
        # 주석은 curl 명령 위나 아래에 따로 작성
        curl -X POST \
          -H "Content-Type: application/json" \
          -d "$REQUEST_BODY" \
          "$API_ENDPOINT"

        # JWT 사용 시:
        # curl -X POST \
        #   -H "Content-Type: application/json" \
        #   -H "Authorization: Bearer $JWT_TOKEN" \
        #   -d "$REQUEST_BODY" \
        #   "$API_ENDPOINT"

        # -----------------------------
        # 요청 사이에 잠시 대기 (0.1초)
        # -----------------------------
        sleep 0.1
    done
done
