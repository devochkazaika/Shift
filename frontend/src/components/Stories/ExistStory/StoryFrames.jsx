import React, { useEffect, useRef } from "react";
import StoryFrame from "./StoryFrame";
import AddFrame from "./AddFrame";
import Button from "../../ui/Button";
import { updateFrameOrder, deleteFrame } from "../../../api/stories";
import { ReactComponent as DragIcon } from "../../../assets/icons/drag.svg";
import styles from "./StoryCard.module.scss";

/**
 * @param {Object} props - Component props
 * @param {Array} frames - Array of story frames
 * @param {number} storyIndex - Index of the story
 * @param {boolean} changeable - Flag to allow changes
 * @param {string} platform - Platform type
 * @param {function} setFrames - Function to update frames
 */
const StoryFrames = ({ frames, setFrames, storyIndex, story, platform, changeable }) => {
  const draggableListRef = useRef(null);

  useEffect(() => {
    const draggableList = draggableListRef.current;
    const initialOrder = frames.map((frame) => frame.id);

    const handleDragStart = (e) => {
      if (e.target.tagName === "LI") e.target.classList.add("dragging");
    };

    const handleDragEnd = async (e) => {
      if (e.target.tagName === "LI") {
        e.target.classList.remove("dragging");
        const newOrder = Array.from(draggableList.children).map((child) => child.id);
        if (newOrder.toString() !== initialOrder.toString()) {
          try {
            await updateFrameOrder(story, platform, newOrder);
            setFrames((prevFrames) => prevFrames.sort((a, b) => newOrder.indexOf(a.id) - newOrder.indexOf(b.id)));
          } catch (error) {
            console.error("Error updating frame order:", error);
          }
        }
      }
    };

    const handleDragOver = (e) => {
      e.preventDefault();
      const afterElement = getDragAfterElement(draggableList, e.clientY);
      const draggingElement = draggableList.querySelector(".dragging");
      if (afterElement == null) draggableList.appendChild(draggingElement);
      else draggableList.insertBefore(draggingElement, afterElement);
    };

    const getDragAfterElement = (list, clientY) => {
      const draggableElements = [...list.querySelectorAll(".draggable:not(.dragging)")];
      return draggableElements.reduce(
        (closest, child) => {
          const box = child.getBoundingClientRect();
          const offset = clientY - box.top - box.height / 2;
          return offset < 0 && offset > closest.offset ? { offset, element: child } : closest;
        },
        { offset: Number.NEGATIVE_INFINITY }
      ).element;
    };

    draggableList.addEventListener("dragover", handleDragOver);
    draggableList.querySelectorAll("li").forEach((draggable) => {
      draggable.addEventListener("dragstart", handleDragStart);
      draggable.addEventListener("dragend", handleDragEnd);
    });

    return () => {
      draggableList.removeEventListener("dragover", handleDragOver);
      draggableList.querySelectorAll("li").forEach((draggable) => {
        draggable.removeEventListener("dragstart", handleDragStart);
        draggable.removeEventListener("dragend", handleDragEnd);
      });
    };
  }, [frames, story, platform, setFrames]);

  const handleOnDelete = async (frame) => {
    const success = await deleteFrame(story, frame, platform);
    if (success) setFrames((prevFrames) => prevFrames.filter((item) => item.id !== frame.id));
  };

  return (
    <div>
      <h3>Карточки</h3>
      <ul ref={draggableListRef}>
        {frames.map((frame, index) => (
          <li id={frame.id} className="listFrame draggable" key={index} draggable="true">
            <details>
              <summary className={styles.summary}>
                <DragIcon className={styles.drag_icon} />
                <p>{frame.title}</p>
                {!changeable && (
                  <Button text="Удалить" type="button" color="red" handleOnClick={() => handleOnDelete(frame)} />
                )}
              </summary>
              <StoryFrame
                changeable={changeable}
                frame={frame}
                frameIndex={index}
                storyIndex={storyIndex}
                story={story}
                platform={platform}
              />
            </details>
          </li>
        ))}
      </ul>
      {!changeable && (
        <details>
          <summary>Добавить карточку</summary>
          <AddFrame setFrames={setFrames} frames={frames} storyIndex={storyIndex} story={story} platform={platform} />
        </details>
      )}
    </div>
  );
};

export default StoryFrames;
