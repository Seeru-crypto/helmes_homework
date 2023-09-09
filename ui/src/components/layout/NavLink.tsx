import React, {useEffect} from "react";
import Link from "next/link";
import styled from "styled-components";
import {useRouter} from "next/router";

const StyledLink = styled.div`
  .passive {
    color: ${({theme}) => theme.primaryColor};
    text-decoration: none;
    margin: 0 0.5rem;
    transition: color 0.3s;
    border: 1px solid ${({theme}) => theme.secondaryColor};
    padding: ${({theme}) => theme.size.$100};
    border-radius: ${({theme}) => theme.size.$100};

    &:hover {
      color: ${({theme}) => theme.secondaryColor};
    }
  }

  .active {
    color: ${({theme}) => theme.secondaryColor};
  }
`;

interface NavLinkProps {
    href: string;
    children: React.ReactNode;
}

const NavLink: React.FC<NavLinkProps> = ({ href, children }) => {
    const router = useRouter();
    const currentRoute = router.pathname;
    const [isActive, setIsActive] = React.useState(false);

    useEffect(() => {
        if (currentRoute == href) {
            setIsActive(true );
        } else {
            setIsActive(false);
        }
    }, [currentRoute, href]);


    return (
        <StyledLink>
            <Link href={href} className={isActive
                ? "active"
                : "passive"}
                  style={{textDecoration: 'none'}} passHref>
                {children}
            </Link>
        </StyledLink>
    );
};

export default NavLink;
