<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Rubik+Mono+One&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="/_img/minecraft.svg">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script> 
        $(document).ready(function(){
        setInterval(function(){
              $("#here").load(" #here > *" );
        }, 3000);
        });
    </script>
    <title>Server State</title>
</head>
<body>
    <img src="../_img/ReBirth-Text.png"/>
    <div id="here">
        
        <?php
            $conn = mysqli_connect("212.22.92.26", "qemgkhrw", "changeme", "qemgkhrw_43131");
            $sql = "SELECT working_state FROM properties";
            $result = $conn->query($sql);
            $row = $result->fetch_assoc();
            if($row["working_state"]==1) {
                echo '<h1>Server is <span style="color: rgb(102, 255, 102)">Working</span></h1>';
            }
            if($row["working_state"]==0) {
                echo '<h1>Server is <span style="color: rgb(255, 80, 80)">Stopped</span></h1>';
            }
            if($row["working_state"]==2) {
                echo '<h1>Server under <span style="color: rgb(255, 255, 102)">Maintenance</span></h1>';
            }
        ?>
    </div>
</body>
</html>