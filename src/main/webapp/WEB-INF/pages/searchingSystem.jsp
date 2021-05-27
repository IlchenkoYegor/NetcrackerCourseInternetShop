<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 27.05.2021
  Time: 01:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<head>
    <title>Search</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <script>
        function toHandle(data){
            data.forEach()
        }
        async function searchInfo(value){
                console.log("KEY PRESSED")
                console.log(value);
                if(value.length >= 2){

                    try{
                    await $.ajax({
                        type: "get",
                        url: `${pageContext.request.contextPath}/findByName`,
                        data: {'name':value},
                        response: 'responseJSON',
                        success: function(data) {
                            var listItems = "";
                            if (data.length == 0) {
                                $(".search_result").html("no Results").fadeIn()
                            } else {
                                for (var i = 0; i < data.length; i++) {
                                    listItems += "<option value='" + data[i].name + "'>" + data[i].name + "</option>";
                                }
                                $("#DLState").html(listItems);

                                $(".search_result").html(listItems).fadeIn()
                            }
                        }

                    })
                    }catch(e){
                        $(".search_result").html("no Results").fadeIn()
                    }
                }
            }

            $(".search_result").hover(function(){
                $(".who").blur();

            })

            $(".search_result").on("click", "li", function(){
                s_user = $(this).text();
                $(".search_result").fadeOut();
            })

    </script>
</head>
<body>
<form name="search" action="${pageContext.request.contextPath}/findByNameAndStay" method="POST">
    Name: <input  type="text" name="name" value="" size="20" placeholder="Type name of product" onkeyup='searchInfo(value)' autocomplete="off" class="who"/><input type="submit" value="Enter" class = "button-send-sc" />
    <center><ul class="search_result"></ul></center>
</form>
</body>
</html>
