<!doctype html>
<html>
<head>
    <title>App Engine Demo</title>
    <script src="//cdn.bootcss.com/jquery/1.10.0/jquery.min.js"></script>
</head>
<body>
<div id="result">Loading...</div>

<script>
    $(document).ready(function () {
        $.getJSON('/demo', function (data) {
            $('#result').html("Hello, " + data.name);
        });
    });
</script>
</body>
</html>
