import React from "react";
import styled from 'styled-components';

const Main = styled.main`
    width: 1920px;
    height: 1080px;
`;

const Section = styled.section`
    img{
        width: 15%;
        height: 50%;
        background-color: white;
        margin-right: 5%;
    }
    div{
        width: 30%;
        height: 70%;
        background-color: white;
        display: flex;
        align-items: center;

        button{
        width: 40%;
        height: 20%;
        margin-right: 10%;
        background-color: #ffa07a;
        color: black;
        font-size: 1rem;
        border: none;
        box-shadow: 1px 1px 5px;
        cursor: pointer;
    }
    .like {
 
        width: 5%;
        height: 10%;
    }
    }
    
    width: 100%;
    height: 40%;
    background-color: #faebd7;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
`;


const RestaurantMain = () => {
    
    return (
    <>
        <Main>
            <Section>
                    <img src='./imges/PIC.jpg' alt="매장 사진" />
                    <div>
                        <p>
                            변수
                        </p>
                        <button className="like">찜</button>
                        <button>예약하기</button>
                        <button>1:1 문의하기</button>
                    </div>
            </Section>
        </Main>
    </>
  )
}

export default RestaurantMain;