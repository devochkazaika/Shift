import React, { useState } from 'react';
import './Request.module.scss'
import Button from '../../ui/Button';
import { rollBackOperation } from '../../../api/history';
import styles from "../../../styles/StoryPanelStyle.module.scss"

const RequestPanel = ({ data }) => {
    const [isDelete, setDelete] = useState(false);
    const handleOnSubmit = async (id) => {
        try{
            rollBackOperation(id);
            setDelete(true);
        }
        catch (exc){
            console.log("Could not get response")
        }
      };
    return (
        <>
        {!isDelete ?
        <li className={styles["listFrame"]}>
            <details>
              <summary draggable="true">
                <div>
                    <table>
                        <tr>
                            <td>{data["bankId"]}</td>
                            <td>{data["platform"]}</td> 
                            <td>{data["bankId"]}</td> 
                            <td>{data["operationType"]}</td>
                            <td>{data["componentType"]}</td>
                            <td>{data["componentId"]}</td>
                            {data["rollBackAble"] ? <td>
                                <Button
                                text="Отозвать"
                                type="button"
                                color="red"
                                handleOnClick={() => handleOnSubmit(data["id"])}
                                />
                            </td> : <></>}
                        </tr>
                
                    </table>
                </div>
              </summary>
            </details>
        </li> : <></>} </>
    )
}

export default RequestPanel