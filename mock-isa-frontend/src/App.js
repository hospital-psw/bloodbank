import React from "react";
import './App.css';
import { Route, Routes } from 'react-router-dom';
import SendMessage from './components/SendMessagePage'; 

function Hello() {
  return (
    <div>
      <p>Hello world!</p>
    </div>
  )
}

function App() {
  return (
    <Routes>
      <Route path='/' element={<Hello />} />
      <Route path='/send' element={<SendMessage />} />
    </Routes>
  );
}

export default App;
