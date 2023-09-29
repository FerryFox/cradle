function ScrollToTopButton() {
    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth' // smooth scroll
        });
    };

    return <button onClick={scrollToTop}>Scroll to Top</button>;
}

export default ScrollToTopButton;