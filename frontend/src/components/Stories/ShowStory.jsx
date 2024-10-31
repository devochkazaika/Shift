import React, { useEffect, useState } from "react";
import { baseURL } from "../../api/api";
import styles from "./ShowStory.module.scss";
import Button from "../ui/Button";

const ShowStory = ({ stories }) => {
    const [storiesArray, setStoriesArray] = useState(stories);
    const [story, setStory] = useState(storiesArray);
    const [frameArray, setFrameArray] = useState(story?.storyFrames || []); // Защита от null

    const [index, setIndex] = useState(0);

    useEffect(() => {
        if (stories) {
            setStoriesArray(stories);
        }
        if (storiesArray) {
            setStory(storiesArray);
        }
        setFrameArray(story?.storyFrames || []); // Защита от возможных ошибок
    }, [stories, storiesArray, story?.storyFrames]);

    // Перелистывание карточек внутри истории
    const rightFrame = () => {
        if (frameArray.length - 1 > index) {
            setIndex(index + 1);
        }
    };

    const leftFrame = () => {
        if (index > 0) {
            setIndex(index - 1);
        }
    };

    return (
        <div style={{ display: "flex" }}>
            <div className={styles.container}>
                {/* Полоска для отображения номера истории */}
                <div className={styles.lines}>
                    {frameArray.slice(0, frameArray.length).map((frame, ind) => (
                        <hr
                            key={ind}
                            style={{
                                backgroundColor: ind <= index ? "white" : "grey",
                                width: `${100 / frameArray.length}%`,
                            }}
                        />
                    ))}
                </div>
                <div className={styles["image-container"]}>
                    <img
                        src={`${baseURL}/image?path=${frameArray[index]?.pictureUrl}`}
                        className={styles["background-image"]}
                        alt=""
                    />
                    <div>
                        {/* Кнопки для перелистывания карточек историй */}
                        <div className={styles.left}>
                            {index > 0 && (
                                <div className={styles["button-frame-browsing"]}>
                                    <Button
                                        text="<"
                                        type="button"
                                        color="grey"
                                        handleOnClick={leftFrame}
                                    />
                                </div>
                            )}
                        </div>
                        <div className={styles.right}>
                            {frameArray.length - 1 > index && (
                                <div className={styles["button-frame-browsing"]}>
                                    <Button
                                        text=">"
                                        type="button"
                                        color="grey"
                                        handleOnClick={rightFrame}
                                    />
                                </div>
                            )}
                        </div>
                    </div>
                    {/* Кнопка у превью карточки */}
                    <div className={styles["text-overlay"]}>
                        {frameArray[index]?.visibleButtonOrNone !== "NONE" && (
                            <div className={styles["button-show-story"]}>
                                <button
                                    style={{
                                        color: frameArray[index]?.buttonTextColor,
                                        backgroundColor: frameArray[index]?.buttonBackgroundColor,
                                    }}
                                >
                                    <div>{frameArray[index]?.buttonText}</div>
                                </button>
                            </div>
                        )}
                        <p style={{ color: frameArray[index]?.textColor }}>
                            {frameArray[index]?.text}
                        </p>
                        <h1 style={{ color: frameArray[index]?.textColor }}>
                            {frameArray[index]?.title}
                        </h1>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ShowStory;
