import http from 'k6/http';
import { sleep } from 'k6';

// 부하 테스트 설정
// 가상 유저 10명 만들어서 10분동안 해당 API에 호출 할꺼다
export let options = {
  vus: 10, // 동시에 접속할 가상 유저 수
  duration: '10m', // 테스트 지속 시간
};

export default function () {
  const url = 'http://localhost:8080/board/form';
  const payload = JSON.stringify({
    title: 'Test Post',
    content: 'This is a test post content',
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, payload, params);

  check(res, {
    '응답 코드가 200인가?': (r) => r.status === 200,
  });

  sleep(1);
}
