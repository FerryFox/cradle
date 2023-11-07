import React, { useState, useEffect } from 'react';
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import {Button, IconButton, Paper, Radio, RadioGroup, Snackbar, Switch, Toolbar} from "@mui/material";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import Controller from "../core/Controller";
import {TemplateModel} from "./model/models";
import {loadTemplateModels} from "./service/templateService";
import CheckIcon from '@mui/icons-material/Check';
import TextField from "@mui/material/TextField";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import axios from "axios";
import FormControlLabel from "@mui/material/FormControlLabel";
import Typography from "@mui/material/Typography";
import DisabledByDefaultIcon from '@mui/icons-material/DisabledByDefault';
export default function Templates()
{
    const [templates, setTemplates] = useState<TemplateModel[]>([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    const [filteredTemplates, setFilteredTemplates] = useState<TemplateModel[]>([]);
    const [searchText, setSearchText] = useState("");
    const [openSnackbar, setOpenSnackbar] = useState(false);

    const [category, setCategory] = useState<string[]>([]);
    const [hideCategory, setHideCategory] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState('');
    useEffect(() => {
        axios('/api/templates/categories')
            .then((response) => setCategory(response.data))
    }, []);

    const handleHideCategoryChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setHideCategory(event.target.checked);
        if (!event.target.checked) {
            setSelectedCategory('');
        }
    }

    const resetFilter = () => {
        setSearchText('');
        setSelectedCategory('');
        setHideCategory(false);
    }

    useEffect(() => {
        const filterTemplates = () => {
            // Start with all templates
            let filtered = templates;

            // Filter by selectedCategory if it is not the default value ('')
            if (selectedCategory !== '') {
                filtered = filtered.filter(template => template.stampCardCategory === selectedCategory);
            }

            // Further filter by searchText if it is not empty
            if (searchText !== '') {
                filtered = filtered.filter(template => template.name.toLowerCase().includes(searchText.toLowerCase()));
            }

            // Set the state with the filtered templates
            setFilteredTemplates(filtered);
        };

        filterTemplates();

    }, [selectedCategory, templates, searchText]);


    const handleGetCardClick = async (templateId: number) => {
        try {
            await createStampCardFromTemplateId(templateId);
            setOpenSnackbar(true);
        } catch (error) {
            console.error("Error creating the stamp card:", error);
        }
    };

    useEffect(() =>
    {
        loadTemplateModels(setTemplates, setLoading)
            .catch( (error) => setError(error.message));
        }, []);


    if (loading) return (
        <Container>
          <p>loading</p>
        </Container>
    )

    if (error) return (
        <div>Error loading data. Please try again later.</div>
    )

    return (
    <div>
     <Controller title={"Public Stamp Cards"}/>
        <Container>
            <Toolbar></Toolbar>

            <Paper elevation={DEFAULT_ELEVATION} sx={{ mt: 3, py :2, px : 2}}>
                <Grid container alignItems="flex-end" spacing={1}>
                    <Grid item xs={12}>
                        <Typography variant="h6" component="div" align={"center"} >
                            Active Filters
                        </Typography>
                    </Grid>

                    <Grid item xs>
                        <TextField
                            value={searchText}
                            onChange={(event) => setSearchText(event.target.value)}
                            label="Filter"
                            variant="outlined"
                            fullWidth
                        />
                    </Grid>
                    <Grid item >
                        <IconButton onClick={resetFilter}>
                            <DisabledByDefaultIcon color={"secondary"} />
                        </IconButton>
                    </Grid>

                    <Grid container alignItems="center" spacing={1}>
                        <Grid item>
                            <Switch
                                checked={hideCategory}
                                onChange={handleHideCategoryChange}
                                color="secondary"
                            />
                        </Grid>
                        <Grid item>
                            <Typography variant="body1">
                                Search by category
                            </Typography>
                        </Grid>
                    </Grid>

                    {hideCategory &&
                    <Grid item xs={12}>
                        <RadioGroup
                            row
                            name="row-radio-buttons-group">
                            {category.map((categoryItem) => (
                                <FormControlLabel
                                    key={categoryItem}
                                    value={categoryItem}
                                    control={
                                        <Radio
                                            onChange={(event) => setSelectedCategory(event.target.value)}
                                        />
                                    }
                                    label={categoryItem}
                                />
                            ))}
                        </RadioGroup>
                    </Grid>
                    }
                </Grid>
            </Paper>

            <Grid container spacing={2} justifyContent="center" sx={{mt : 3}}>
                {filteredTemplates.map(template => (
                    <>
                    <Grid item xs={6} key={template.id + "get"} >
                        <Template templateModel={template} key={template.id + "template"}/>
                        <Button onClick={() => handleGetCardClick(template.id)}>Get This Card</Button>
                    </Grid>
                    </>
                ))}
            </Grid>

            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                open={openSnackbar}
                autoHideDuration={2000}
                onClose={() => setOpenSnackbar(false)}
                message="Stamp card created"
                action={
                    <IconButton color="primary" size="small" onClick={() => setOpenSnackbar(false)}>
                        <CheckIcon />
                    </IconButton>
                }
            />
        </Container>
    </div>
    );
}

