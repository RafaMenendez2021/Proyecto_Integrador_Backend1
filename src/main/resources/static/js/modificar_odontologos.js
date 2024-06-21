document.addEventListener("DOMContentLoaded", function() {
    const formModificar = document.getElementById("modificarForm");

    // Obtener el ID de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const odontologoId = urlParams.get('id');

    // Cargar los datos del odontólogo
    if (odontologoId) {
        fetch(`odontologo/${odontologoId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                document.getElementById("nombre").value = data.nombre;
                document.getElementById("apellido").value = data.apellido;
                document.getElementById("matricula").value = data.matricula;
            })
            .catch(error => {
                console.error("Error fetching odontologo data:", error);
            });
    }

    // Manejar el evento de submit del formulario
    formModificar.addEventListener("submit", function(event) {
        event.preventDefault();

        const updatedNombre = document.getElementById("nombre").value;
        const updatedApellido = document.getElementById("apellido").value;
        const updatedMatricula = document.getElementById("matricula").value;

        // Llamar al endpoint de modificación
        fetch(`odontologo/${odontologoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ id: odontologoId, nombre: updatedNombre, apellido: updatedApellido, matricula: updatedMatricula }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.text(); // Leer como texto primero
            })
            .then(responseText => {
                console.log("Response text:", responseText);
                alert(responseText); // Mostrar el mensaje de texto
                window.location.href = `/listar_odontologos.html`;
            })
            .catch(error => {
                console.error("Error modificando odontólogo:", error);
            });
    });
});
