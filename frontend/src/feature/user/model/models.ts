export type AdditionalInfoDTO = {
    name?: string;
    id?: number;
    bio?: string | null;
    status?: string;
    connection?: string;
    picture?: string;
}

export type AppUserDTO =
    {
        id: number;
        appUserName: string;
        appUserEmail: string;
        nameIdentifier: string;

        addInfoDTO?: AdditionalInfoDTO;
        friends?: AppUserDTO[];
    }