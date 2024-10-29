import { React, useEffect, useState } from "react";
import { approveChanging, deleteChangingRequest, getUnApprovedChangedStoriesByBank } from "../../../api/stories";
import styles from "../../../styles/StoryPanelStyle.module.scss";
// import Button from "../../ui/Button";
import PreviewStory from "../ExistStory/PreviewStory";
import Button from "../../ui/Button";

const PreviewChangeRequest = ({bankId, platform}) => {
  // const [currentPage, setCurrentPage] = React.useState(1);
  const [storiesArray, setStoriesArray] = useState([]);

  const approve = async (storyElem) => {
    try {
      const success = await approveChanging(storyElem["historyId"]);
      if (success) {
        console.log(success)
        setStoriesArray((prevArray) =>
          prevArray
            .map((item) => ({
              ...item,
              changes: item.changes.filter((change) => change !== storyElem),
            }))
            .filter((item) => item.changes.length > 0)
        );
      }
    } catch (error) {
      console.error("Error approving story:", error);
    }
  };
  const deleteRequest = async (story) => {
    try{
      const success = await deleteChangingRequest(story["historyId"]);
      if (success) {
        // storiesArray.forEach(item => {
        //   if (item )
        // })
      }
    }
    catch (error){
      console.error("Error approve story:", error);
    }
  }

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getUnApprovedChangedStoriesByBank(bankId, platform);
        if (data == null) {
          setStoriesArray([]);
        } else {
          setStoriesArray(data);
          // setCurrentPage(1);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);

  return (
    <div>
      <h1>Запросы на изменение Превью историй</h1>

      {storiesArray.length > 0 ? (
        <ul className="stories">
          <h2>{platform}</h2>
          {storiesArray.map((story, index) => (
            <>
              {story["changes"].map((storyElem, indexChange) => (
                <li className={styles["listFrame"]} key={indexChange}>
                  <details>
                    <summary>
                      <p>{story.story.previewTitle}</p>
                      <div className="row">
                        <Button
                          text="Удалить"
                          type="button"
                          color="red"
                          handleOnClick={() => deleteRequest(storyElem)}
                        />
                        <Button
                          text="Принять"
                          type="button"
                          color="green"
                          handleOnClick={() => approve(storyElem)}
                        />
                      </div>
                    </summary>
                    <div>
                      <PreviewStory
                        story={story.story}
                        storyIndex={index}
                        platform={platform}
                        changeable={true}
                      />
                      <PreviewStory
                        story={storyElem.storyPresentation}
                        storyIndex={index}
                        platform={platform}
                        changeable={true}
                      />
                    </div>
                  </details>
                </li>
              ))}
            </>
          ))}
        </ul>
      ) : (
        <h2>Пока нет историй</h2>
      )}
    </div>
  )
}

export default PreviewChangeRequest;