<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>登录</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" href="css/amazeui.min.css">
  <link rel="stylesheet" href="css/app.css">
</head>
<body>
<div class="container">
<div class="top"><h1>404+</h1></div>
<div class ="main">
<div class="mainimg"></div>
<div class="mlogin">
<div class="am-g myapp-login">
	<div class="myapp-login-bg"  style="min-height:500px">
		  <div data-am-widget="tabs"
	       class="am-tabs am-tabs-d2"
	        >
	      <ul class="am-tabs-nav am-cf">
	          <li class="am-active"><a href="[data-tab-panel-0]">登录</a></li>
	        
	         
	      </ul>
	      <div class="am-tabs-bd">
	          <div data-tab-panel-0 class="am-tab-panel am-active">
				<form action="" class="am-form">
					<fieldset>
						<div class="am-form-group">
						<label for="doc-vld-name">用户账号</label>
						<input type="text" id="doc-vld-name" minlength="1" placeholder="User ID" class="am-form-field" required/>
						</div>
						<div class="am-form-group">
						<label for="doc-vld-name">用户密码</label>
						<input type="password" id="doc-vld-name" minlength="3" placeholder="User Password" class="am-form-field" required/>
						</div>
						<button class="myapp-login-button am-btn am-btn-secondary" type="submit">登录</button>
					</fieldset>
					<legend>忘记密码</legend>
				</form>
	          </div>
	        
	      </div>
	  </div>
	</div>
</div>
</div>
</div>
<div class="foot">
	  <footer data-am-widget="footer"
          class="am-footer am-footer-default"
           data-am-footer="{  }">
    <div class="am-footer-switch">
    <span class="am-footer-ysp" data-rel="mobile"
          data-am-modal="{target: '#am-switch-mode'}">
          404+
    </span>
      <span class="am-footer-divider"> | </span>
      <a id="godesktop" data-rel="desktop" class="am-footer-desktop" href="javascript:">
          会议室预定系统
      </a>
    </div>
    <div class="am-footer-miscs ">

          <p>由 <a href="http://www.yunshipei.com/" title="诺亚方舟"
                                                target="_blank" class="">404+</a>
            提供技术支持</p>
        <p>CopyRight©2018  </p>
        <p>华农404</p>
    </div>
  </footer>

</div>
</div>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="js/amazeui.min.js"></script>
<script src="js/app.js"></script>
</body>
</html>