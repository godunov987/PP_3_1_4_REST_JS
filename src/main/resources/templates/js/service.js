document.onclick = event => {
    switch (event.target.id) {
        case 'buttonAdmin':
            // alert(this.id);
            break;
        case 'buttonUser':
            // alert('buttonUser');
            break;
        case 'nav-profile-new':
            insertSelectorRoles(document.querySelector('#newUserAddDivAllFilds'));
            break;
    }
}


async function insertSelectorRoles(objInput) {
    getPromiseAllRoles().then(result => addRolesSelector(result));

    function addRolesSelector(roles) {
        let optionalSelectRole = objInput.querySelector('#roles');
        while (optionalSelectRole.firstChild) {
            optionalSelectRole.removeChild(optionalSelectRole.firstChild);
        }
        optionalSelectRole.size = roles.length;
        for (let i = 0; i < roles.length; i++) {
            let optionRoles = document.createElement('option');
            optionRoles.setAttribute('value', roles[i]);
            optionRoles.textContent = roles[i];
            optionalSelectRole.append(optionRoles);
        }
    }
}

async function openModal(id, type) {
    let modal = document.querySelector("#modalWindows");

    insertSelectorRoles(modal);

    getPromiseUser(id).then(user => setUserFilds(user));

    function setUserFilds(user) {
        let button = modal.querySelector('#buttonEndOderDelete');
        modal.querySelector('.modal-title').innerHTML = type + ' user';
        button.innerHTML = type;
        for (let userKey in user) {
            let el = modal.querySelector('#' + userKey);
            if (el != null) {
                el.value = user[userKey];
                console.log(el.getAttribute('value'))
            }
            if(el.hasAttribute("viewFalse")){
                el.value = '';
            }
        }
        user = null;
        if (type === "Delete") {
            button.setAttribute("class", "btn btn-danger");
            modal.querySelector('#passFild').style.display = 'none';
            button.setAttribute('onclick', 'userDelete(' + id + ')');
        } else {
            button.setAttribute("class", "btn btn-primary");
            modal.querySelector('#passFild').style.display = 'inline';
            button.setAttribute('onclick', 'userEdit( )');
        }
    }
}

function userDelete(id) {               //59 строка этого .js
    deleteUser(id).then(() => addUserTable());
}

function userEdit() {                   //63 строка этого .js
    let user = getJsonUser('modalWindows');
    if(user){
        sendUser(user, 'PUT').then(() => addUserTable());
    }
}

function getJsonUser(serchInputFildIdContainer) {
    let newUserAdd = document.querySelector("#" + serchInputFildIdContainer).querySelectorAll('.form-control');

    if (newUserAdd[4].value.indexOf('@') === -1 || newUserAdd[4].value.length < 3) {
        showError('errorEmail', 'Email должен содержать @ и не может быть меньше трёх элементов');
        return false;
    }

    return {
        "id": parseInt(newUserAdd[0].value),
        "name": newUserAdd[1].value,
        "lastname": newUserAdd[2].value,
        "email": newUserAdd[4].value,
        "age": parseInt(newUserAdd[3].value),
        "password": newUserAdd[5].value,
        "roles": Array.from(newUserAdd[6]).filter(o => o.selected).map(el => el.value)
    };
}

function showError(fildId, text) {
    let fild = document.querySelector('#' + fildId);
    fild.removeAttribute('hidden');
    fild.innerHTML = text;
    setTimeout(() => {
        fild.setAttribute('hidden', 'true')
    }, 3000);
}

function selectorAdminPanel() {
    let navPanel = document.querySelector('#navigationPanelForAdmin');
    let butUserTab = [navPanel.querySelector('#nav-home-tab'), navPanel.querySelector('#table')];
    let butNewUser = [navPanel.querySelector('#nav-profile-new'), navPanel.querySelector('#new')];

    butUserTab[0].setAttribute('class', 'nav-item nav-link active');
    butUserTab[0].setAttribute('aria-selected', 'true');
    butUserTab[1].setAttribute('class', 'tab-pane fade active show');

    butNewUser[0].setAttribute('class', 'nav-item nav-link');
    butNewUser[0].setAttribute('aria-selected', 'false');
    butNewUser[1].setAttribute('class', 'tab-pane fade');

    addUserTable();

    document.querySelector("#newUserAddDivAllFilds").querySelectorAll('.form-control').forEach(x => x.value = '');

}