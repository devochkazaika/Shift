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
            <div className="lines">
            {frameArray.slice(0, frameArray.length).map((frame, index) => (
                (index <= ind) ?
                <hr 
                    style={{
                        backgroundColor: "white",
                        width: `${100 / frameArray.length}%`,
                        height: "3px",
                        marginRight: "1%",
                        border: "none",
                        opacity: "80%",
                    }} 
                    key={index} 
                />
                : <hr 
                    style={{
                        backgroundColor: "grey", 
                        width: `${100 / frameArray.length}%`,
                        height: "3px",
                        marginRight: "1%",
                        border: "none",
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
                <div className="text-overlay" style={{ display: "flex", flexDirection: "column-reverse", justifyContent: "flex-start", height: "100%", width: "100%" }}>
                    {frameArray[ind].visibleButtonOrNone !== "NONE" && (
                        <div style={{ textAlign: "center", paddingTop: "10%" }}>
                            <button
                                style={{
                                    color: frameArray[ind].buttonTextColor,
                                    backgroundColor: frameArray[ind].buttonBackgroundColor,
                                    fontSize: "1px",
                                    // padding: "5px px",
                                    textOverflow: "ellipsis",
                                    height: "35px",
                                    width: "95%",
                                    overflow: "hidden",
                                    textAlign: "center",
                                    boxSizing: "border-box"
                                }}
                            >
                                <div style={{ fontSize: "12px", paddingLeft: "10px", fontFamily: 'Verdana', textAlign: "center", overflowWrap: "break-word" }}>
                                    {frameArray[ind].buttonText}
                                </div>
                            </button>
                        </div>
                    )}
                    <p style={{
                        color: frameArray[ind].textColor,
                        wordWrap: "break-word", /* Для переноса текста */
                        width: "100%", /* Ограничение по ширине */
                        overflow: "hidden",
                        boxSizing: "border-box",
                        whiteSpace: "pre-wrap",
                        fontSize: "12px",
                        fontWeight: "550",
                    }}>
                        {frameArray[ind].text}
                    </p>
                    <h1 style={{
                        color: frameArray[ind].textColor,
                        wordWrap: "break-word", /* Для переноса текста */
                        width: "100%", /* Ограничение по ширине */
                        overflow: "hidden", /* Если необходимо скрыть текст при переполнении */
                        boxSizing: "border-box",
                        fontSize: "25px"
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