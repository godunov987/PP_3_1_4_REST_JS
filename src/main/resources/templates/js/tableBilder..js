async function addUserTable() {
    let tbody = document.createElement('tbody');
    tbody.id = "userContainer";

    getPromiseUser().then(list => Array.from(list).forEach(user => addUserForTable(user)));


    function addUserForTable(user) {
        let table = document.querySelector('#bodyTableAllUsers');
        let lineUserParam = document.createElement('tr');

        lineUserParam.innerHTML = '<td scope="row">' + user.id + '</td>\n' +
            '<td >' + user.name + '</td>\n' +
            '<td >' + user.lastname + '</td>\n' +
            '<td >' + user.age + '</td>\n' +
            '<td >' + user.email + '</td>\n' +
            '<td >' + user.roles.join(" ") + '</td>\n' +
            '<td><button type="button" data-toggle="modal" class="btn btn-info" data-target="#modalWindows" onclick="openModal(' + user.id + ', \'Edit\')">Edit</button></td>' +
            '<td><button type="button" data-toggle="modal" class="btn btn-danger" data-target="#modalWindows" onclick="openModal(' + user.id + ', \'Delete\')">Delete</button></td>';

        table.append(lineUserParam);
    }
    $('#bodyTableAllUsers').html(tbody);
}