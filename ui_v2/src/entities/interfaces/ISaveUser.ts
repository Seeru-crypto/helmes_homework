export interface ISaveUser {
    name: string;
    sectorIds: number[];
    agreeToTerms: boolean;
    email?: string;
    phoneNumber?: string;
    dob?: string
}