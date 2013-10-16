<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="/assets/bootstrap/assets/ico/favicon.png">

  <title>Login</title>

  <!-- Bootstrap core CSS -->
  <link href="/assets/bootstrap/dist/css/bootstrap.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/assets/css/app.css${assetToken}" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    <script src="/assets/bootstrap/assets/js/html5shiv.js"></script>
    <script src="/assets/bootstrap/assets/js/respond.min.js"></script>
  <![endif]-->


  <script src="/assets/js/app.js${assetToken}"></script>
</head>

<body>

<div class="container">

  <form class="form-signin" action="login/auth" method="post">
    <h2 class="form-signin-heading">Please sign in</h2>
    <input name="redirectTo" type="hidden" value="${redirectTo?html}">
    <input name="user" type="text" class="form-control" placeholder="Guidewire Domain Login" autofocus>
    <input name="password" type="password" class="form-control" placeholder="Password">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  </form>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/assets/bootstrap/assets/js/jquery.js"></script>
<script src="/assets/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>
