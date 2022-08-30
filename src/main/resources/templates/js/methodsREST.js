async function sendUser(raw, method) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
        method: method,
        headers: myHeaders,
        body: JSON.stringify(raw),
        redirect: 'follow'
    };

    return fetch("http://localhost:8080/api/add", requestOptions);
}

async function getPromiseUser(id = "all") {
    let myHeaders = new Headers();
    myHeaders.append("type", id);
    myHeaders.append("Cookie", "JSESSIONID=5161E7E86C4A5FF9F7B625D63EA0B563");

    let requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    return fetch("http://localhost:8080/api/json", requestOptions)
        .then(response => response.ok ? response.json() : alert("ERROR download user"))
        .catch(error => console.log('error', error));

}

async function getPromiseAllRoles() {
    let myHeaders = new Headers();
    myHeaders.append("Cookie", "JSESSIONID=DFE762A422823953B4ED8ECA9B469328");

    let requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    return fetch("http://localhost:8080/api/roles", requestOptions)
        .then(response => response.json())
        .catch(error => console.log('error', error));
}

async function deleteUser(id) {
    var myHeaders = new Headers();
    myHeaders.append("id", id);

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        redirect: 'follow'
    };

    return fetch("http://localhost:8080/api/delete", requestOptions)
        .then(response => response.text())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}

