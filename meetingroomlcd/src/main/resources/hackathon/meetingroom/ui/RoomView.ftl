<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="/assets/bootstrap/assets/ico/favicon.png">

  <title>Room ${name?html}</title>

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

<div class="container alert alert-info" id="msgContainer">
  Message
</div>

<div class="container" id="mainContainer" style="display:none;">


  <div class="starter-template">
    <h1>
      <span class="glyphicon glyphicon-ok text-success" id="iconAvailable"></span>
      <span class="glyphicon glyphicon-lock text-danger" id="iconBusy"></span>
      Room ${name?html}
    </h1>

    <div class="lead" id="headerBusy">
      <div class="noInfo">
        <p>This room is being occupied</p>
      </div>
      <div class="info">
        <p>Occupied for <b class="duration">45 min</b><span style="display:none;">by <span class="owner">Darrel</span></span></p>
        <p>Meeting: "<span class="title">Studio bi-weekly update</span>"</p>
      </div>
    </div>

    <div class="lead" id="headerAvailable">
      <div class="noInfo">
        <p>Available forever, please book me now!</p>
      </div>
      <div class="info">
        <p>Available for <b class="duration">45 min</b></p>
        <p>Next Meeting: "<span class="title">Studio bi-weekly update</span>"<span style="display:none;">, <span class="owner">Darrel</span></span></p>
      </div>
    </div>

  </div>

  <div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4" id="buttons">
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
        <p>
          <button type="button" class="btn btn-primary btn-block btn-lg">Book for 1 h 59 m</button>
        </p>
    </div>
  </div>
</div>
<!-- /.container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/assets/bootstrap/assets/js/jquery.js"></script>
<script src="/assets/bootstrap/dist/js/bootstrap.min.js"></script>
<script>
  jQuery(function() {
    update('${name?js_string}');
  });
</script>
</body>
</html>
