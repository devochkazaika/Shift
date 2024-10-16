import React, {useEffect, useState } from "react";
import { baseURL } from "../../api/api";
import styles from "./showstory.css"
import Button from "../ui/Button";

const ShowStory = (stories) => {
    const [storiesArray, setStoriesArray] = useState(stories["stories"]);
    const [story, setStory] = useState(storiesArray);
    const [frameArray, setFrameArray] = useState(story["storyFrames"]);

    const [index, setIndex] = useState(0);

    useEffect(() => {
        if (stories) {
            setStoriesArray(stories["stories"]);
        }
        if (storiesArray) {
            setStory(storiesArray);
        }
        setFrameArray(story["storyFrames"]);
    }, [stories]);

    // Перелистывание карточек внутри истории
    const rightFrame = () => {
        if (frameArray.length - 1 > index){
            setIndex(index + 1);
        }
    };

    const leftFrame = () => {
        if (index > 0){
            setIndex(index - 1);
        }
    };

    return (
        <>
        <div style={{display: "flex"}}>
        <div className={styles.container}>
            {/* Полоска для отображения номера истории */}
            <div className="lines">
            {frameArray.slice(0, frameArray.length).map((frame, ind) => (
                (ind <= index) ?
                <hr 
                    style={{
                        backgroundColor: "white",
                        width: `${100 / frameArray.length}%`
                    }} 
                    key={ind} 
                />
                : <hr 
                    style={{
                        backgroundColor: "grey", 
                        width: `${100 / frameArray.length}%`
                    }} 
                    key={ind} 
                />
            ))}
            </div>
            <div className="image-container">
                <img
                    src={`${baseURL}/image?path=${frameArray[index].pictureUrl}`}
                    className="background-image"
                    alt=""
                />
                <div>
                    { /* Кнопки для перелистывания карточек историй */}
                    <div className="left">
                        {(index > 0) ? <div className="buttonFrameBrowsing">
                            <Button
                            text="<"
                            type="button"
                            color="grey"
                            handleOnClick={leftFrame}/>
                        </div>
                        : <></>}
                    </div>
                    <div className="right">
                        {(frameArray.length-1 > index) ? <div className="buttonFrameBrowsing">
                            <Button
                            text=">"
                            type="button"
                            color="grey"
                            handleOnClick={rightFrame}/>
                        </div> 
                        : <></>}
                    </div>
                </div>
                <div className="text-overlay">
                    {frameArray[index].visibleButtonOrNone !== "NONE" && (
                        <div className="button-show-story">
                            <button
                                style={{
                                    color: frameArray[index].buttonTextColor,
                                    backgroundColor: frameArray[index].buttonBackgroundColor,
                                }}>
                                <div>
                                    {frameArray[index].buttonText}
                                </div>
                            </button>
                        </div>
                    )}
                    <p style={{
                        color: frameArray[index].textColor,
                    }}>
                        {frameArray[index].text}
                    </p>
                    <h1 style={{
                        color: frameArray[index].textColor,
                    }}>
                        {frameArray[index].title}
                    </h1>
                </div>
            </div>
        </div>
        </div>
        </>
    );

}

export default ShowStory;