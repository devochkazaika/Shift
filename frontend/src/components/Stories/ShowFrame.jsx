import React, {useEffect, useState } from "react";
import styles from '../UploadImage/PreviewImage/PreviewImage.module.scss';
import { baseURL } from "../../api/api";

const ShowStory = (fr) => {


    return (
        <>
         {console.log(story["previewUrl"])}
            <h1>Превью</h1>
            <div className="grid-container">
                <img style={{position: "absolute", marginLeft: "30px", marginTop: "17px", width: "450px", height: "660px"}}
                    className={styles.preview_image}
                    alt="preview"
                    src={`${baseURL}/image?path=${story["previewUrl"]}`}
                    width="100px"
                    height="100px"
                    // onClick={() => toggleModel(true)}
                />
                <img 
                    className={styles.preview_image}
                    alt="preview"
                    src={`${baseURL}/iphone.png`}
                    width="100px"
                    height="100px"
                    // onClick={() => toggleModel(true)}
                    />
                <div className="item1">
                    <h1 style={{color: "black"}}>asdasd</h1>
                </div>
            </div>
        </>
    );

}

export default ShowStory;