import React, { useState } from "react";

export default function SendMessage() {
    const [title, setTitle] = useState("");
    const [body, setBody] = useState("");
    const [image, setImage] = useState("");

    const readImageAsURL = (imageFile) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onerror = reject;
            reader.onload = () => {
                resolve(reader.result);
            }
            reader.readAsDataURL(imageFile)
        })
    }

    const onUploadImage = (event) => {
        Promise.resolve(readImageAsURL(event.target.files[0]))
        .then((res) => {setImage(res)})
    }

    const onSubmit = (event) => {
        let req = {
            "reqTitle": title,
            "reqBody": body,
            "reqImage": image
        }

        fetch("http://localhost:8081/send", {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(req)
        })
        .then(res => res.json())
        .then(data => {
            console.log(data)
        });

        event.preventDefault();
    }

    return (
        <div>
            <form style={{alignItems:'center'}} onSubmit={(e) => onSubmit(e)}>
                <label>
                    Title:
                    <input type={'text'} value={title} onChange={(e) => setTitle(e.target.value)} />
                </label>
                <br /><br />
                <label>
                    Body:
                    <textarea value={body} onChange={(e) => setBody(e.target.value)} />
                </label>
                <br /><br />
                <label>
                    image:
                    <input type={'file'} onChange={(e) => onUploadImage(e)} />
                </label>
                <br /><br />
                <input type={'submit'} value={'Send'} />
            </form>
        </div>
    );
}