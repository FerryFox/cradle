import React, {useEffect} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import {TemplateModel} from "./model/models";
import {Button, Divider} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";

interface TemplateProps {
    templateModel: TemplateModel;
    getButton? : (id: number) => void;
}

export default function Template({ templateModel, getButton }: TemplateProps)
{
    const [isFront, setIsFront] = React.useState(true);
    const [displayName , setDisplayName] = React.useState("");
    const [date , setDate] = React.useState("");

    useEffect(() => {
        if (templateModel) {
            let namePart = templateModel.createdBy.split('-')[0];
            let name = namePart.split('#')[0];
            setDisplayName(name);

            let dateObj = new Date(templateModel.expirationDate);
            let isoString = dateObj.toISOString()
            setDate(isoString.split('T')[0]);
        }
    }, [templateModel]);

    return (
        <Card onClick={() => setIsFront(!isFront)} elevation={DEFAULT_ELEVATION} sx={{
            borderRadius: 3,
            position: 'relative',
            minWidth:"50%",
            height: '34vh',}}>
            <CardMedia
                sx={{ height: "17vh" }}
                image={templateModel.image}
                title="green iguana"
            />
            {isFront && (
            <CardContent
                sx={{ display: 'flex',
                      flexDirection: 'column',
                      height: '100%',
                      fontWeight: 'bold' }}>
                <Typography variant="h5" align="left" >
                    {templateModel.name}
                </Typography>

                <Divider color={"error"}  style={{ marginBottom: '8px'}}/>

                <Typography variant="body2" style={{ display: 'block', textAlign: 'left' }}>
                     by  {displayName}
                </Typography>

                <Typography variant="body2" style={{ display: 'block', textAlign: 'left' }}>
                    {date}
                </Typography>

                <Typography variant="body2" style={{ display: 'block', textAlign: 'left' }}>
                    {templateModel.promise}
                </Typography>

                <Typography  variant="body2"
                             color={"secondary"}
                             style={{
                                 position: 'absolute',
                                 bottom: '8px',
                                 right: '8px'}}>

                    {templateModel.stampCardCategory}
                </Typography>
            </CardContent>)
            }
            {!isFront && (
                <CardContent
                    sx={{ display: 'flex',
                        flexDirection: 'column',
                        height: '100%',
                        fontWeight: 'bold' }}>
                    <Typography variant="h5" align="left" >
                        {templateModel.name}
                    </Typography>

                    <Divider color={"error"}  style={{ marginBottom: '8px'}}/>

                    <Typography variant="body2" style={{ textAlign: 'left', overflowWrap: 'break-word'}}>
                       {templateModel.description}
                    </Typography>

                    {getButton ?
                        (
                            <Button onClick={() => getButton(templateModel.id)}
                                    style={{
                                        position: 'absolute',
                                        bottom: '2px',
                                        right: '2px'}}>

                                Get
                            </Button>
                        )
                            :
                        (
                            <Typography  variant="body2"
                                            color={"secondary"}
                                            style={{
                                                position: 'absolute',
                                                bottom: '8px',
                                                right: '8px'}}>

                            INFO
                        </Typography>
                        )
                    }

                </CardContent>)
            }


        </Card>
    );
}
