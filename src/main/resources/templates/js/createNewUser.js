function addNewUser(serchInputFildIdContainer) {
    let user = getJsonUser(serchInputFildIdContainer);
    if (user) {
        for (let userKey in user) {
            let fildVal = user[userKey];
            if (userKey !== "id") {
                if (fildVal === '' || Number.isNaN(fildVal)) {
                    showError('errorNewForm', 'Нужно заполнить все поля!');
                    return;
                }
            }
        }
        sendUser(user, 'POST')
            .then(res => res.ok ? selectorAdminPanel() : showError('errorNewForm', 'Такой email зарегистирован'));
    }
}