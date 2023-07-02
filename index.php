<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Varela+Round&display=swap" rel="stylesheet">
    <title>Server State</title>

</head>
<body onload="getStatement();">
    <div class="statement-card">
        <h1>ReBirth 7</h1>
        <?php
            $conn = mysqli_connect("212.22.92.26", "qemgkhrw", "changeme", "qemgkhrw_43131");
            $sql = "SELECT working_state FROM properties";
            $result = $conn->query($sql);
            if ($result==2) {
                echo "<h1>Server is Running</h1>";
            }
        ?>
    </div>
</body>
</html>