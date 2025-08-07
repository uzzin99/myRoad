$("#signBtn").click(function () {

    const username = $('input[name=username]').val();
    const password = $('input[name=password]').val();

    if(!username){
        alert("이메일을 입력하세요");
        return false;

    }

    if(!password){
        alert("비밀번호를 입력하세요");
        return false;
    }

    const loginData = {
      username: username,
      password: password
    };

    axios.post('/api/auth/sign-in', loginData).then(response => {

        const token = response.data;
        localStorage.setItem('accessToken', token.accessToken);
        localStorage.setItem('refreshToken', token.refreshToken);

        axios.defaults.headers.common['Authorization'] = `Bearer ${token.accessToken}`;

        // ✅ 로그인 성공 시 메인 페이지로 이동
        window.location.href = "/schedule"; // 또는 "/"

     })
     .catch(error => {
        alert('이메일 또는 비밀번호가 잘못되었습니다.');
     });

});