import http from 'k6/http';
import { sleep } from 'k6';

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
  //sleep(1);
}
