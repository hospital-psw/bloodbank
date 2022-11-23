import React from "react";
import "./App.css";
import { useNavigate } from "react-router-dom";
import { Route, Routes } from "react-router-dom";
import SendMessage from "./components/SendMessagePage";
import PreviewPdf from "./components/PreviewPdf";

function Hello() {
  const navigate = useNavigate();
  return (
    <div>
      <button onClick={() => navigate("/previewPdf")}>PDF</button>
      <p>Hello world!</p>
    </div>
  );
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<Hello />} />
      <Route path="/send" element={<SendMessage />} />
      <Route path="/previewPdf" element={<PreviewPdf />} />
    </Routes>
  );
}

export default App;
