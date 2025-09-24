<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<div class="footer-wrapper">
  <footer class="footer">
    <div class="footer_content">
      <p>© 2025 MOOD SHOP. All rights reserved.</p>

      <!-- 사업자 정보 -->
      <div class="toggle_item">
        <h4 class="toggle_title">사업자 정보 <span class="toggle_icon">+</span></h4>
        <div class="toggle_content">
          <p>
            (주) MOOD SHOP | 대표자 : KOKONE <br>
            주소 : 서울 감성구 무드로 101 감성동 56 <br>
            호스팅사업자 : (주)MOOD SHOP <br>
            통신판매업 : 2025-서울감성-051125 <br>
            사업자등록번호 : 123-45-678910
          </p>
        </div>
      </div>

      <!-- 고객지원 -->
      <div class="toggle_item">
        <h4 class="toggle_title">고객지원 <span class="toggle_icon">+</span></h4>
        <div class="toggle_content">
          <a href="gotoQnA.do" style="color: #ccc; text-decoration: underline;">Q&A</a><br>
          <a href="gotoMyQnA.do" style="color: #ccc; text-decoration: underline;">1:1 문의하기</a>
        </div>
      </div>
    </div>
  </footer>
</div>

<style>
.footer-wrapper {
  width: 100%;
  background: #000;
  box-shadow: 0 -2px 10px rgba(0,0,0,0.3);
}

.footer {
  max-width: 100%;
  margin: 0 auto;
  padding: 15px 10px;
  color: #fff;
  text-align: center;
}

.footer p {
  margin-bottom: 15px;
  font-size: 13px;
}

.toggle_title {
  cursor: pointer;
  font-weight: bold;
  margin: 10px 0 8px;
  user-select: none;
  display: inline-flex;
  align-items: center;
  gap: 6px; 
  transition: color 0.3s;
  font-size: 14px; 
}

.toggle_title:hover {
  color: #ff7f50;
}

.toggle_icon {
  font-weight: bold;
  font-size: 14px;
  transition: transform 0.3s;
}

.toggle_content {
  font-size: 13px; 
  color: #ccc;
  max-width: 500px; 
  margin: 0 auto 15px;
  line-height: 1.5;
  display: none;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.toggle_content.show {
  display: block;
  opacity: 1;
}

.toggle_content p {
  text-align: center;
  white-space: normal;
  word-break: keep-all;
  line-height: 1.5;
  margin: 0 auto;
}

.toggle_content a:hover {
  color: #ff7f50;
}
</style>

<script>
document.querySelectorAll('.toggle_title').forEach(title => {
  title.addEventListener('click', function () {
    const content = this.nextElementSibling;
    const icon = this.querySelector('.toggle_icon');

    if (content.classList.contains('show')) {
      content.classList.remove('show');
      icon.textContent = '+';
    } else {
      content.classList.add('show');
      icon.textContent = '-';
    }
  });
});
</script>
