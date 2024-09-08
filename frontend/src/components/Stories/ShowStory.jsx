import React, {useEffect, useState } from "react";
// import styles from '../UploadImage/PreviewImage/PreviewImage.module.scss';
import { baseURL } from "../../api/api";
import styles from "./showstory.css"
import Button from "../ui/Button";

const ShowStory = (stories) => {
    const [storiesArray, setStoriesArray] = useState(stories["stories"]);
    const [story, setStory] = useState(storiesArray);
    const [frameArray, setFrameArray] = useState(story["storyFrames"]);

    const [ind, setIndex] = useState(0);

    useEffect( () => {
        if (stories) setStoriesArray(stories["stories"]);
        if (storiesArray) setStory(storiesArray);
        setFrameArray(story["storyFrames"])
    }, [storiesArray]);

    const rightFrame = () => {
        if (frameArray.length-1 > ind){
            setIndex(ind+1);
        }
        console.log(ind);
    };

    const leftFrame = () => {
        if (ind > 0){
            setIndex(ind-1);
        }
        console.log(ind);
    };

    return (
        <>
        {console.log(ind)}
        <div style={{display: "flex"}}>
        <div className={styles.container}>
            <div className="lines">
            {frameArray.slice(0, frameArray.length).map((frame, index) => (
                (index <= ind) ?
                <hr 
                    style={{
                        backgroundColor: "white", // Изменяем цвет линии
                        width: `${100 / frameArray.length}%`,
                        height: "3px", // Высота линии
                        marginRight: "1%",
                        border: "none", // Убираем стандартное обрамление
                        opacity: "80%",
                    }} 
                    key={index} 
                />
                : <hr 
                    style={{
                        backgroundColor: "grey", // Цвет линии для остальных элементов
                        width: `${100 / frameArray.length}%`,
                        height: "3px", // Высота линии
                        marginRight: "1%",
                        border: "none", // Убираем стандартное обрамление
                        opacity: "80%",
                    }} 
                    key={index} 
                />
            ))}
            </div>
            <div className="image-container">
                <img
                    src={`${baseURL}/image?path=${frameArray[ind].pictureUrl}`}
                    alt="Story frame"
                    className="background-image"
                />
                <div>
                    <div className="left">
                        {(ind > 0) ? <Button
                            text="<"
                            type="button"
                            color="grey"
                            handleOnClick={() => leftFrame()}
                        /> : <></>}
                    </div>
                    <div className="right">
                        {(frameArray.length-1 > ind) ? <Button
                            text=">"
                            type="button"
                            color="grey"
                            handleOnClick={() => rightFrame()}
                        /> : <></>}
                    </div>
                </div>
                <div className="text-overlay">
                    <h1 style={{color: frameArray[ind].textColor}}>{frameArray[ind].title}</h1>
                    <p style={{color: frameArray[ind].textColor}}>{frameArray[ind].text}</p>
                    {frameArray[ind].visibleButtonOrNone!="NONE" ? 
                    <div style={{textAlign: "center"}}>
                        <button 
                            style={{
                                color: frameArray[ind].buttonTextColor,
                                backgroundColor: frameArray[ind].buttonBackgroundColor,
                                fontSize: "clamp(16px, 2.5vw, 50px)",
                                padding: "5px 5px",
                                textOverflow: "ellipsis",
                                height: "40%",
                                width: "90%",
                                overflow: "hidden",
                                textAlign: "center"
                            }}
                        >
                            <div style={{fontSize: "25px", maxWidth: "100%", wordBreak: "break-word"}}>{frameArray[ind].buttonText}</div>
                        </button>
                    </div>
                    : <></>}
                </div>

            </div>
        </div>
        </div>
        </>
    );

}

export default ShowStory;