const tableBody = document.querySelector("#odontologosTable tbody");
const formModificar = document.getElementById("modificarForm");

function fetchOdontologos() {
  // Listando los odontólogos
  fetch(`odontologo`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
          <td>${odontologo.id}</td>
          <td>${odontologo.nombre}</td>
          <td>${odontologo.apellido}</td>
          <td>${odontologo.matricula}</td>
          <td>
            <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.matricula}')">Modificar</button>
            <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
          </td>
        `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

function editOdontologo(id) {
    window.location.href = `/modificar_odontologos.html?id=${id}`;
  }
  function deleteOdontologo(id){
    let respuesta = confirm("¿Desea eliminar al odontologo?");
    if(respuesta){
        fetch(`odontologo/${id}`, {
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
                alert("Odontologo eliminado");
                window.location.href = `/listar_odontologos.html`;
            })
            .catch(error => {
                console.error("Error modificando odontólogo:", error);
            });
    }
  }
  
fetchOdontologos();
