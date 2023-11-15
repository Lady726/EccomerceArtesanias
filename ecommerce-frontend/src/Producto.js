import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button, Col, Container, Form, Navbar, Row, Table } from "react-bootstrap";
import './styles.css';


export default function Producto() {
  const [editingProducto, setEditingProducto] = useState(null);
  const [productos, setProductos] = useState([]);
  const [newProducto, setNewProducto] = useState({
    codigoEan: "",
    nombreDelProducto: "",
    descripcionDelProducto: "",
    marcaArtesania: "", unidadesDisponibles: "",
    imagenProducto: "",
    tipo: "",
    detail: "",
    correo: ""


  });
  const [showAddForm, setShowAddForm] = useState(false);
  const showEdit = (producto) => {
    setEditingProducto(producto.id); // Guarda el ID del producto que estás editando
    setNewProducto(producto);
    setShowAddForm(true);
  }

  useEffect(() => {
    axios.get("http://localhost:8080/api/producto/")
      .then(response => setProductos(response.data))
      .catch(error => console.log(error));
  }, []);

  const addProducto = () => {
    axios.post("http://localhost:8080/api/producto/", newProducto)
      .then(() => {
        // Actualizar los productos desde el backend
        axios.get("http://localhost:8080/api/producto/")
          .then(response => setProductos(response.data))
          .catch(error => console.log(error));

        setNewProducto({
          codigoEan: "",
          nombreDelProducto: "",
          descripcionDelProducto: "",
          marcaArtesania: "",
        });
        setEditingProducto(null);
        setShowAddForm(false);
      })
      .catch(error => console.log(error));
    

  };

  const deleteProducto = (id) => {
    axios.delete(`http://localhost:8080/api/producto/${id}`)
      .then(() => {
        setProductos(productos.filter(producto => producto.id !== id));
      })
      .catch(error => console.log(error));
  };
  const updateProducto = (id, updatedProducto) => {
    axios.put(`http://localhost:8080/api/producto/${id}`, updatedProducto)
      .then(response => {
        setProductos(productos.map(producto =>
          producto.id === id ? response.data : producto
        ));
        setEditingProducto(null);
        setShowAddForm(false);  // Cerrar el formulario de edición
      })
      .catch(error => console.log(error));
   
  };

  const profesiones = [
    "Ingeniero de Software",
    "Médico Cirujano",
    "Abogado",
    "Arquitecto",
    "Psicólogo"
  ];
  
  const programasUniversidad = [
    "Ingeniería Informática",
    "Medicina",
    "Derecho",
    "Arquitectura",
    "Psicología"
  ];
  

  return (
    <Container fluid>
      <Navbar className ="px-3"bg="dark" variant="dark">
        <Navbar.Brand  href="#home">Formulario</Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse className="justify-content-end">
          <Button variant="outline-light" onClick={() => setShowAddForm(!showAddForm)}>
            {showAddForm ? 'Cerrar' : 'Agregar Producto'}
          </Button>
        </Navbar.Collapse>
      </Navbar>
      <Row>
        {showAddForm && (
          <Col>
            <h1 className="custom-title">Registro</h1>
            <Form>

              <Form.Group className="mb-3">
                <Form.Control
                  type="text"
                  placeholder="DNI"
                  value={newProducto.codigoEan}
                  onChange={(e) => setNewProducto({ ...newProducto, codigoEan: e.target.value })}
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Control
                  as="select"
                  value={newProducto.tipo}
                  onChange={(e) => {
                    setNewProducto({ ...newProducto, tipo: e.target.value, detail: "" });
                  }}
                >
                  <option value="" disabled>Selecciona tipo</option>
                  <option value="estudiante">Estudiante</option>
                  <option value="profesor">Profesor</option>
                </Form.Control>

              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Control
                  as="select"
                  disabled={newProducto.tipo?.length == 0 ? true : false}
                  placeholder={newProducto.tipo?.length == 0 ? "Debe seleccionar que tipo de usuario es" : ""}
                  value={newProducto.detail}
                  onChange={(e) => setNewProducto({ ...newProducto, detail: e.target.value })}
                >
                  {newProducto.tipo === "profesor" && profesiones.map(profesion => (
                    <option key={profesion} value={profesion}>{profesion}</option>
                  ))}
                  {newProducto.tipo?.length == 0 && (
                    <option key={"profesion"} value={""}>{"Debe seleccionar un tipo"}</option>
                  )}
                  {newProducto.tipo === "estudiante" && programasUniversidad.map(programa => (
                    <option key={programa} value={programa}>{programa}</option>
                  ))}
                </Form.Control>
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Control
                  type="email"
                  placeholder="Correo"
                  value={newProducto.correo}
                  onChange={(e) => setNewProducto({ ...newProducto, correo: e.target.value })}
                />
              </Form.Group>
              <Button className="custom-button" onClick={editingProducto ? () => updateProducto(editingProducto, newProducto) : addProducto}>
                {editingProducto ? 'Actualizar Producto' : 'Agregar Producto'}
              </Button>

            </Form>
          </Col>
        )}
        <Table striped bordered hover className="mt-3">
          <Col>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th scope="col">DNI</th>
                  <th scope="col">Tipo</th>
                  <th scope="col">Correo</th>
                  <th scope="col">Detail</th>
                  <th scope="col">Acciones</th>
                </tr>
              </thead>
              <tbody>
                {productos.map((producto, index) => (
                  <tr key={index}>
                    <td>{producto.codigoEan}</td>
                    <td>{producto.tipo}</td>
                    <td>{producto.correo}</td>
                    <td>{producto.detail}</td>
                    
                    <td>
                    <div className="d-flex justify-content-center g-3" style={{gap:10}}>
                      <Button variant="info" onClick={() => showEdit(producto)}>Editar</Button>
                      <Button variant="danger" onClick={() => deleteProducto(producto.id)}>Eliminar</Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </Col>
        </Table>
      </Row>
    </Container>
  );


}

