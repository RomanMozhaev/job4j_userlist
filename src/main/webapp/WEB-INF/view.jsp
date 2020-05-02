<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function updateForm(id) {
            var url = "${pageContext.servletContext.contextPath}/updateForm?id=" + id;
            $.ajax({
                type: "POST",
                url: url,
                success: [function ($data) {
                    updateFormProcessing($data);
                }],
                dataType: 'json'
            });
        }

        function updateFormProcessing($data) {
            if ($data['status'] === 'valid') {
                document.getElementById('id').value = $data['id'];
                document.getElementById('name').value = $data['name'];
                document.getElementById('email').value = $data['email'];
                document.getElementById('city').value = $data['city'];
                document.getElementById('state').value = $data['state'];
            } else {
                alert("The user was not found. Please try again.");
            }
        }

        function submitForm() {
            var formData = readFields();
            if (validate(formData)) {
                if ($('#id').val() === "") {
                    var url = "${pageContext.servletContext.contextPath}/create";
                    sendRequest(formData, url, true);
                } else {
                    var url = "${pageContext.servletContext.contextPath}/update";
                    sendRequest(formData, url, false);
                }
            }
        }

        function readFields() {
            var formData = new FormData();
            formData.append("id", $('#id').val());
            formData.append("name", $('#name').val());
            formData.append("email", $('#email').val());
            formData.append("city", $('#city').val());
            formData.append("state", $('#state').val());
            var pass = $('#pass').val();
            if (pass === undefined) {
                pass = "";
            }
            formData.append("pass", pass);
            formData.append("role", $('#role').val());
            var pic = document.getElementById("pic").files[0];
            if (pic !== undefined) {
                formData.append("pic", pic);
            }
            return formData;
        }

        function validate(data) {
            var message = "";
            if (data.name === "") {
                message += "name";
            }
            if (data.email === "") {
                if (message !== "") {
                    message += ", ";
                }
                message += "email";
            }
            if (data.pass === "") {
                if (message !== "") {
                    message += ", ";
                }
                message += "password";
            }
            if (message !== "") {
                alert("Please enter: " + message);
                return false;
            } else {
                return true;
            }
        }

        function sendRequest(formData, url, action) {
            $.ajax({
                type: "post",
                url: url,
                data: formData,
                success: [function ($data) {
                    if (action) {
                        create(formData, $data['status'], $data['id'], $data['pic'], $data['date']);
                    } else {
                        update(formData, $data['status'], $data['pic']);
                    }
                }],
                contentType: false,
                processData: false,
            });
        }

        function create(formData, status, id, pic, date) {
            var msg1 = "The user was successfully added.";
            var msg2 = "The user was not added. Please try again.";
            if (resultMessage(status, msg1, msg2)) {
                addRow(formData, id, pic, date);
            }
            resetFields();
        }

        function update(formData, status, pic) {
            var msg1 = "The user was successfully updated.";
            var msg2 = "The user was not updated. Please try again.";
            if (resultMessage(status, msg1, msg2)) {
                updateRow(formData, pic);
            }
            resetFields();
        }

        function deleteUser(id) {
            var url = "${pageContext.servletContext.contextPath}/delete?action=delete&id=" + id;
            $.ajax({
                type: "post",
                url: url,
                success: [function ($data) {
                    var msg1 = "The user was deleted successfully.";
                    var msg2 = "The user was not deleted. Please try again.";
                    if (resultMessage($data['status'], msg1, msg2)) {
                        deleteRow(id)
                    }
                }],
                contentType: 'json',
            });

        }

        function resultMessage(status, msg1, msg2) {
            var valid;
            if (status === 'valid') {
                alert(msg1);
                valid = true;
            } else {
                alert(msg2);
                valid = false
            }
            return valid;
        }

        function addRow(formData, id, pic, date) {
            var tbody = document.getElementById('table').getElementsByTagName("TBODY")[0];
            var row = document.createElement("TR");

            var td1 = document.createElement("TD");
            var div1 = document.createElement("DIV");
            div1.setAttribute('id', id + "_id");
            div1.appendChild(document.createTextNode(id));
            td1.appendChild(div1);
            row.appendChild(td1);

            var td2 = document.createElement("TD");
            var div2 = document.createElement("DIV");
            div2.setAttribute('id', id + "_role");
            div2.appendChild(document.createTextNode(formData.get('role')));
            td2.appendChild(div2);
            row.appendChild(td2);

            var td3 = document.createElement("TD");
            var div3 = document.createElement("DIV");
            div3.setAttribute('id', id + "_name");
            div3.appendChild(document.createTextNode(formData.get('name')));
            td3.appendChild(div3);
            row.appendChild(td3);

            var td4 = document.createElement("TD");
            var div4 = document.createElement("DIV");
            div4.setAttribute('id', id + "_email");
            div4.appendChild(document.createTextNode(formData.get('email')));
            td4.appendChild(div4);
            row.appendChild(td4);

            var td5 = document.createElement("TD");
            var div5 = document.createElement("DIV");
            div5.setAttribute('id', id + "_city");
            div5.appendChild(document.createTextNode(formData.get('city')));
            td5.appendChild(div5);
            row.appendChild(td5);

            var td6 = document.createElement("TD");
            var div6 = document.createElement("DIV");
            div6.setAttribute('id', id + "_state");
            div6.appendChild(document.createTextNode(formData.get('state')));
            td6.appendChild(div6);
            row.appendChild(td6);

            var td7 = document.createElement("TD");
            var div7 = document.createElement("DIV");
            div7.setAttribute('id', id + "_date");
            var dateStamp = new Date(date);
            console.log(dateStamp.toLocaleString());
            div7.appendChild(document.createTextNode(dateStamp.toLocaleString()));
            td7.appendChild(div7);
            row.appendChild(td7);

            var td8 = document.createElement("TD");
            var form8 = document.createElement("FORM");
            form8.setAttribute('id', id + "_pic");
            var a8 = document.createElement("A");
            var link8 = ctx + "/download?photoId=" + pic;
            var img8 = document.createElement("IMG");
            img8.setAttribute('src', link8);
            img8.setAttribute('width', "100px");
            img8.setAttribute('height', "100px");
            a8.appendChild(img8);
            a8.setAttribute('href', link8);
            form8.appendChild(a8);
            // form8.appendChild(img8);
            td8.appendChild(form8);
            row.appendChild(td8);

            var td9 = document.createElement("TD");
            var but1 = document.createElement("BUTTON");
            but1.setAttribute('type', "submit");
            but1.setAttribute('onclick', "deleteRow('" + id + "')");
            but1.setAttribute('class', "form-control");
            but1.appendChild(document.createTextNode("delete"));
            td9.appendChild(but1);
            row.appendChild(td9);

            var td10 = document.createElement("TD");
            var but2 = document.createElement("BUTTON");
            but2.setAttribute('type', "submit");
            but2.setAttribute('onclick', "updateForm('" + id + "')");
            but2.setAttribute('class', "form-control");
            but2.appendChild(document.createTextNode("update"));
            td10.appendChild(but2);
            row.appendChild(td10);

            tbody.appendChild(row);
        }

        function updateRow(formData, pic) {
            var id = formData.get('id');
            document.getElementById(id + "_name").innerText = formData.get('name');
            document.getElementById(id + "_email").innerText = formData.get('email');
            document.getElementById(id + "_city").innerText = formData.get('city');
            document.getElementById(id + "_state").innerText = formData.get('state');
            var role = formData.get('role');
            if (role !== "none") {
                document.getElementById(id + "_role").innerText = role;
            }
            if (pic !== "") {
                var href = ctx + "/download?photoId=" + pic;
                var element = document.getElementById(id + "_pic");
                element.getElementsByTagName('a')[0].setAttribute('href', href);
                element.getElementsByTagName('img')[0].setAttribute('src', href);
            }
        }

        function resetFields() {
            var form = document.getElementById('modify');
            var inputs = form.getElementsByTagName('input');
            for (var input of inputs)
                input.value = '';
            $("#role").val('none');
        }

        function deleteRow(id) {
            document.getElementById(id + "_id").parentElement.parentElement.remove();
        }

    </script>
    <script>var ctx = "<%=request.getContextPath()%>"</script>
</head>
<body>
<div class="container" style="background: #ffffff;position: fixed; top: 0px; right: 10px; left: 10px">
    <table id="modify" class="table">
        <h2>Create/update the user</h2>
        <thead>
        <tr>
            <th>ID</th>
            <th>Role</th>
            <th>Name</th>
            <th>Password</th>
            <th>Email</th>
            <th>City</th>
            <th>State</th>
            <th>Picture</th>
        </tr>
        </thead>
        <tr>
            <td style="width: 60px;">
                <input type="text" class="form-control" name="id" id="id" readonly>
            </td>
            <td style="width: 100px;">
                <select name="role" class="form-control" id="role">
                    <option value="none"></option>
                    <option value="user">user</option>
                    <option value="admin">admin</option>
                </select>
            </td>
            <td>
                <input type="text" class="form-control" name="name" id="name">
            </td>
            <td>
                <input type="password" class="form-control" name="password" id="pass">
            </td>
            <td>
                <input type="text" class="form-control" name="email" id="email">
            </td>
            <td>
                <input type="text" class="form-control" name="city" id="city">
            </td>
            <td>
                <input type="text" class="form-control" name="state" id="state">
            </td>
            <td>
                <input type="file" class="form-control" name="pic" id="pic">
            </td>
            <td>
                <button type="submit" onclick="submitForm()" class="form-control">submit</button>
            </td>
            <td>
                <button type="submit" onclick="resetFields()" class="form-control">reset</button>
            </td>
        </tr>
    </table>
</div>
<div class="container" style="margin-top:10%">
    <table id="table" class="table">
        <h2>Users</h2>
        <thead>
        <tr>
            <th>ID</th>
            <th>Role</th>
            <th>Name</th>
            <th>Email</th>
            <th>City</th>
            <th>State</th>
            <th>Date</th>
            <th>Picture</th>
        </tr>
        </thead>

        <c:forEach items="${users}" var="entry">
            <tr id="${entry.value.id}">
                <td>
                    <div id="${entry.value.id}_id">
                        <c:out value="${entry.value.id}"></c:out>
                    </div>
                </td>
                <td>
                    <div id="${entry.value.id}_role">
                        <c:out value="${entry.value.role}"></c:out>
                    </div>
                </td>
                <td>
                    <div id="${entry.value.id}_name">
                        <c:out value="${entry.value.name}"></c:out>
                    </div>
                </td>
                <td>
                    <div id="${entry.value.id}_email">
                        <c:out value="${entry.value.email}"></c:out>
                    </div>
                </td>
                <td>
                    <div id="${entry.value.id}_city">
                        <c:out value="${entry.value.city}"></c:out>
                    </div>
                </td>
                <td>
                    <div id="${entry.value.id}_state">
                        <c:out value="${entry.value.state}"></c:out>
                    </div>
                </td>
                <jsp:useBean id="myDate" class="java.util.Date"/>
                <c:set target="${myDate}" property="time" value="${entry.value.createDate}"/>
                <td>
                    <fmt:formatDate pattern="M/DD/YYYY, h:m:s a" value = "${myDate}"/>
                    </td>
                </td>
                <td>
                    <form id="${entry.value.id}_pic">
                        <a href="${pageContext.servletContext.contextPath}/download?photoId=${entry.value.photoId}">
                            <img src="${pageContext.servletContext.contextPath}/download?photoId=${entry.value.photoId}"
                                 width="100px" height="100px"/>
                        </a>
                    </form>
                </td>


                <td>
                    <button type="submit" onclick="deleteUser('${entry.value.id}')" class="form-control">delete</button>
                </td>
                <td>
                    <button type="submit" onclick="updateForm('${entry.value.id}')" class="form-control">update</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>