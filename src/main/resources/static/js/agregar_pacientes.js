const form = document.getElementById("agregarForm");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const dni = document.getElementById("dni").value;
    const fechaIngreso = document.getElementById("fechaIngreso").value;
    const calle = document.getElementById("calle").value;
    const numero = document.getElementById("numero").value;
    const localidad = document.getElementById("localidad").value;
    const provincia = document.getElementById("provincia").value;

    // Llamando al endpoint de agregar paciente
    fetch(`paciente`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            nombre,
            apellido,
            dni,
            fechaIngreso,
            domicilio: { calle, numero, localidad, provincia }
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            alert("Paciente agregado con éxito");
            form.reset(); // Resetear el formulario
            window.location.href = "../listar_pacientes.html";
        })
        .catch((error) => {
            console.error("Error agregando paciente:", error);
        });
});
