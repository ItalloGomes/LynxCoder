function getFeedbacks(user) {
    fetch(`feedbacks/${user.id_usuario}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(feedbacks => {
                if (feedbacks[0].length == 0) {
                    document.getElementById("relInd").innerHTML =
                        `Nada por aqui...`
                }
                feedbacks[0].forEach(feedback => {
                    document.getElementById("relInd").innerHTML +=
                        `<div class="cards_rep">
                            <h2>${feedback.descricao_sprint}</h2>
                            <button onclick="openFeedbackForm(${feedback.id_feedback}, '${feedback.descricao_sprint}')">Preencher</button>
                        </div>`
                });
            });
        } else {
            console.log("Erro ao recuperar feedbacks do banco");
        }
    });
}

function openFeedbackForm(feedbackId, sprintName) {
    document.getElementById("inputFkFeedback").value = feedbackId;
    document.getElementById("spnSprintName").innerHTML = sprintName;
    document.getElementById("boxFeedback").classList.remove("ocult");
}

function closeForm() {
    document.getElementById("boxFeedback").classList.add("ocult");
}

function submitFeedback() {
    var form = new URLSearchParams(new FormData(formFeedback));
    fetch("/feedbacks/preencher", {
        method: "POST",
        body: form
    }).then(function (response) {

        if (response.ok) {
            console.log("Feedback registrado!");
            window.location.reload();
        } else {
            console.log("Erro ao registrar feedback")
        }
    });
    return false;
}
