import React from 'react';
import style from './Request.module.scss'
import Button from '../../ui/Button';

const RequestPanel = ({ data }) => {
    const handleOnSubmit = async (id) => {
        console.log(id);
        // const success = await deleteFrame(story, frame, platform);
        // if (success) {
        //   setFrames((prevFrames) =>
        //     prevFrames.filter((item) => item.id !== frame.id)
        //   );
        // }
      };
    return (
        <li className="listFrame">
            <details>
              <summary draggable="true">
                <div>
                    <table>
                        <tr className={style.tr}>
                            <td>{data["bankId"]}</td>
                            <td>{data["platform"]}</td> 
                            <td>{data["bankId"]}</td> 
                            <td>{data["operationType"]}</td>
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
        </li>
    )
}

export default RequestPanel