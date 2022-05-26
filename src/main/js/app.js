/**
 * Frontend application entrypoint which renders inside the HTML element "react" from index.html.
 * Define routes using React Router for other pages.
 *
 * @author Jean Dorancy
 */

import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import { Routes } from "react-router";
import { BrowserRouter, Route } from "react-router-dom";
import HomeContainer from "./home-container";
import Layout from "./layout";
import Login from "./login";
import "bootstrap/dist/css/bootstrap.css";

const App = () => {
  const [alert, setAlert] = useState({ show: false });

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout setAlert={setAlert} alert={alert} />}>
          <Route index element={<HomeContainer setAlert={setAlert} />} />
          <Route path="login" element={<Login />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

createRoot(document.getElementById("react")).render(<App />);
