document.addEventListener("DOMContentLoaded", function() {
    const formModificar = document.getElementById("modificarForm");

    // Obtener el ID de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const pacienteId = urlParams.get('id');

    // Cargar los datos del paciente
    if (pacienteId) {
        fetch(`paciente/${pacienteId}`)
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
                document.getElementById("dni").value = data.dni;
                document.getElementById("fechaIngreso").value = data.fechaIngreso;

                // Verificar si 'domicilio' existe antes de acceder a sus propiedades
                if (data.domicilio) {
                    document.getElementById("calle").value = data.domicilio.calle || '';
                    document.getElementById("numero").value = data.domicilio.numero || '';
                    document.getElementById("localidad").value = data.domicilio.localidad || '';
                    document.getElementById("provincia").value = data.domicilio.provincia || '';
                }
            })
            .catch(error => {
                console.error("Error fetching paciente data:", error);
            });
    }

    // Manejar el evento de submit del formulario
    formModificar.addEventListener("submit", function(event) {
        event.preventDefault();

        const updatedNombre = document.getElementById("nombre").value;
        const updatedApellido = document.getElementById("apellido").value;
        const updatedDni = document.getElementById("dni").value;
        const updatedFechaIngreso = document.getElementById("fechaIngreso").value;
        const updatedCalle = document.getElementById("calle").value;
        const updatedNumero = document.getElementById("numero").value;
        const updatedLocalidad = document.getElementById("localidad").value;
        const updatedProvincia = document.getElementById("provincia").value;

        // Llamar al endpoint de modificación
        fetch(`paciente/${pacienteId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: pacienteId,
                nombre: updatedNombre,
                apellido: updatedApellido,
                dni: updatedDni,
                fechaIngreso: updatedFechaIngreso,
                domicilio: {
                    calle: updatedCalle,
                    numero: updatedNumero,
                    localidad: updatedLocalidad,
                    provincia: updatedProvincia
                }
            }),
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
                window.location.href = `../listar_pacientes.html`;
            })
            .catch(error => {
                console.error("Error modificando paciente:", error);
            });
    });
});
