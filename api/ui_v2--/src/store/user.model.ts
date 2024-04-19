export interface IUser {
  id?: string;
  name: string;
  agreeToTerms?: boolean;
  email?: string;
  dob?: string;
  phoneNumber?: string;
  sectors?: string[];
}

export const defaultValue: Readonly<IUser> = {
  name: ""
};
