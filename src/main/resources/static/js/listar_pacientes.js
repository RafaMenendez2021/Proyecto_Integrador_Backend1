const tableBody = document.querySelector("#pacientesTable tbody");

function fetchPacientes() {
    // Listando los pacientes
    fetch(`paciente`)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((data) => {
            console.log("Data received:", data);

            if (Array.isArray(data)) {
                // Limpiar el contenido actual de la tabla
                tableBody.innerHTML = "";

                // Insertar los datos en la tabla
                data.forEach((paciente) => {
                    const row = document.createElement("tr");
                    // Crear las celdas de la fila
                    row.innerHTML = `
                        <td>${paciente.id}</td>
                        <td>${paciente.nombre}</td>
                        <td>${paciente.apellido}</td>
                        <td>${paciente.dni}</td>
                        <td>${paciente.fechaIngreso}</td>
                        <td>${paciente.domicilio ? `${paciente.domicilio.calle} ${paciente.domicilio.numero}, ${paciente.domicilio.localidad}, ${paciente.domicilio.provincia}` : 'N/A'}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="editPaciente(${paciente.id})">Modificar</button>
                            <button class="btn btn-danger btn-sm" onclick="deletePaciente(${paciente.id})">Eliminar</button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                console.error("Unexpected data format:", data);
            }
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}

function editPaciente(id) {
    window.location.href = `/modificar_pacientes.html?id=${id}`;
}

function deletePaciente(id) {
    let respuesta = confirm("¿Desea eliminar al paciente?");
    if (respuesta) {
        fetch(`paciente/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.text(); // Leer como texto primero
            })
            .then(responseText => {
                console.log("Response text:", responseText);
                alert("Paciente eliminado");
                window.location.href = `../listar_pacientes.html`;
            })
            .catch(error => {
                console.error("Error eliminando paciente:", error);
            });
    }
}

fetchPacientes();
