import './App.css';
import {useEffect, useState} from "react";

function App() {
    const [message, setMessage] = useState("");

    useEffect(() => {
        fetch('http://localhost:8080/')
            .then(response => response.text())
            .then(message => {
                setMessage(message);
            });
    },[])
    return (
        <div className="App">
            <header className="">
                <h2 className="App-title">{message}</h2>
            </header>
        </div>
    )
}

export default App;