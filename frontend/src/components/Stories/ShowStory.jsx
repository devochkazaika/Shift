import React, {useEffect, useState } from "react";
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
        setFrameArray(story["storyFrames"]);
    }, [stories]);

    // Перелистывание карточек внутри истории
    const rightFrame = () => {
        if (frameArray.length-1 > ind){
            setIndex(ind+1);
        }
    };

    const leftFrame = () => {
        if (ind > 0){
            setIndex(ind-1);
        }
    };

    return (
        <>
        <div style={{display: "flex"}}>
        <div className={styles.container}>
            {/* Полоска для отображения номера истории */}
            <div className="lines">
            {frameArray.slice(0, frameArray.length).map((frame, index) => (
                (index <= ind) ?
                <hr 
                    style={{
                        backgroundColor: "white",
                        width: `${100 / frameArray.length}%`
                    }} 
                    key={index} 
                />
                : <hr 
                    style={{
                        backgroundColor: "grey", 
                        width: `${100 / frameArray.length}%`
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
                        {(ind > 0) ? <div style={{opacity: "60%"}}>
                            <Button
                            text="<"
                            type="button"
                            color="grey"
                            handleOnClick={() => leftFrame()}/>
                        </div>
                             : <></>}
                    </div>
                    <div className="right">
                        {(frameArray.length-1 > ind) ? <div style={{opacity: "60%"}}>
                            <Button
                            text=">"
                            type="button"
                            color="grey"
                            handleOnClick={() => rightFrame()}/>
                        </div>    
                         : <></>}
                    </div>
                </div>
                <div className="text-overlay">
                    {frameArray[ind].visibleButtonOrNone !== "NONE" && (
                        <div style={{ textAlign: "center", paddingTop: "10%" }}>
                            <button
                                className="buttonShow"
                                style={{
                                    color: frameArray[ind].buttonTextColor,
                                    backgroundColor: frameArray[ind].buttonBackgroundColor,
                                }}
                            >
                                <div>
                                    {frameArray[ind].buttonText}
                                </div>
                            </button>
                        </div>
                    )}
                    <p style={{
                        color: frameArray[ind].textColor,
                    }}>
                        {frameArray[ind].text}
                    </p>
                    <h1 style={{
                        color: frameArray[ind].textColor,
                    }}>
                        {frameArray[ind].title}
                    </h1>
                </div>
            </div>
        </div>
        </div>
        </>
    );

}

export default ShowStory;